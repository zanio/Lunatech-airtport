package model

/**
 * Project working on lunatech-airport
 * New File created by ani in  lunatech-airport @ 13/10/2022  12:05
 */
case class Airport(id:Int,ident:Option[String], `type`:Option[String], name:String,latitude_deg: Option[Float], longitude_deg: Option[Float],
                   elevation_ft: Option[Int], continent: Option[String], iso_country: Option[String], iso_region: Option[String], municipality: Option[String],
                   scheduled_service: Option[String], gps_code: Option[String], iata_code: Option[String], local_code: Option[String],
                   home_link: Option[String], wikipedia_link: Option[String], keywords: Option[String])

case class Country(id:Int, code:String,name:String,continent:Option[String], wikipedia_link: Option[String], keywords:Option[String])

case class Runway(id: Int, airport_ref: Option[Int], airport_ident: Option[String], length_ft: Option[Int], width_ft: Option[Int], surface: Option[String],
                  lighted: Option[Boolean], closed: Option[Boolean], le_ident: Option[String], le_latitude_deg: Option[Float],
                  le_longitude_deg: Option[Float], le_elevation_ft: Option[Int], le_heading_degt: Option[Float], le_displaced_threshold_ft: Option[Int],
                  he_ident: Option[String], he_latitude_deg: Option[Float], he_longitude_deg: Option[Float], he_elevation_ft: Option[Int],
                  he_heading_degt: Option[Float], he_displaced_threshold_ft: Option[Int])
