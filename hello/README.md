# Notes from SBT Tutorial


## `build.sbt`

- `build.sbt` contains sub-projects
- each project is typically created as a `lazy val` to avoid initialisation order issues
- each project has `settings` which are key-value pairs

#### Evaluation of keys
- key types are
  - `SettingKey[T]` a value __computed only once__
  - `TaskKey[T]` __recomputed every time__ it's called
  - `InputKey[T]` task with command line arguments
- setting key therefore cannot depend on task key
- a given key always refers to either a task or a plain setting, i.e. “taskiness” (whether to re-run each time) is a property of the key, not the value


## `project` directory
- contains dependencies as `.scala` files
- contains `build.properties` which lists `sbt` version

## Task graph
- rather than thinking of `settings` as key-value pairs, a better analogy would be to think of it as _directed acyclic graph_ (DAG)
- to declare a dependency on another task we use `.value` method
- `.value` is lifted outside the definition as a macro outside of the task's body and run before the current task, so for example guarding it with an if statement will do nothing useful
```
scalacOptions := {
  val ur = update.value
  if (false) {
    val x = clean.value
  }
  ur.allConfigurations.take(3)
}
```
will produce
```
> run
[info] Updating {file:/xxx}root...
[info] Resolving jline#jline;2.14.1...
[info] Done updating.
[info] Running example.Hello
hello
[success] Total time: 0 s, completed Jan 2, 2017 10:45:19 PM
> scalacOptions
[info] Updating {file:/xxx/}root...
[info] Resolving jline#jline;2.14.1 ...
[info] Done updating.
[success] Total time: 0 s, completed Jan 2, 2017 10:45:23 PM
```
if you check for `target/scala-2.12/classes/`, it won’t exist because clean task has run even though it is inside the `if (false)`
- also multiple dependencies are not evaluated in any guaranteed order so `update` might run before or after `clean`
