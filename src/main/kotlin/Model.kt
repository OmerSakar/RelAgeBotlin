import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.dao.*
import org.sqlite.SQLiteConfig
import java.awt.image.DataBufferShort
import java.sql.Connection

object Birthday : Table() {
    val chatID        = integer("chatId")
    val fullName     = varchar("fullName", 50)
    val age           = integer("age")
    val lastModified = long("lastModified")
    val alreadyAsked = bool("alreadyAsked")
}



fun setupDatabase(fileName: String, inMemory: Boolean = true) {
    var url: String =  "jdbc:sqlite:file:" +  fileName + ".db"
    url += if (inMemory) "?mode=memory&cache=shared" else ""
    Database.connect(url, "org.sqlite.JDBC")

    transaction(Connection.TRANSACTION_SERIALIZABLE, 42) {
        logger.addLogger(StdOutSqlLogger)

        create(Birthday)
    }}

fun main(args: Array<String>) {
    setupDatabase("testDB", false)
}

