package util

/**
 * Project working on lunatech-airport
 * New File created by ani in  lunatech-airport @ 13/10/2022  14:06
 */

import com.github.tminglei.slickpg._

trait MyPostgresProfile
  extends ExPostgresProfile
    with PgArraySupport
    with PgDateSupport
    with PgDate2Support
    with PgPlayJsonSupport
    with PgNetSupport
    with PgLTreeSupport
    with PgRangeSupport
    with PgHStoreSupport
    with PgSearchSupport {

  override val pgjson = "jsonb"

  override val api: APIx = new APIx {}

  trait APIx
    extends API
      with ArrayImplicits
      with SimpleDateTimeImplicits
      with DateTimeImplicits
      with PlayJsonImplicits
      with NetImplicits
      with LTreeImplicits
      with RangeImplicits
      with HStoreImplicits
      with SearchImplicits
      with SearchAssistants

  val plainAPI: APIx
    with ByteaPlainImplicits
    with SimpleArrayPlainImplicits
    with Date2DateTimePlainImplicits
    with SimpleNetPlainImplicits
    with SimpleLTreePlainImplicits
    with SimpleRangePlainImplicits
    with SimpleHStorePlainImplicits
    with SimpleSearchPlainImplicits = new APIx
    with ByteaPlainImplicits
    with SimpleArrayPlainImplicits
    with Date2DateTimePlainImplicits
    with PlayJsonPlainImplicits
    with SimpleNetPlainImplicits
    with SimpleLTreePlainImplicits
    with SimpleRangePlainImplicits
    with SimpleHStorePlainImplicits
    with SimpleSearchPlainImplicits {}
}

object MyPostgresProfile extends MyPostgresProfile

