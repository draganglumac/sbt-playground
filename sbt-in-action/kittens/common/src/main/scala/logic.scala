object Logic {
  def matchLikelihood(kitten: Kitten, buyer: BuyerPreferences): Double = {
    val matches = buyer.attributes.map { attr =>
      if (kitten.attributes.contains(attr)) 1.0
      else 0
    }
    matches.sum / matches.length
  }
}
