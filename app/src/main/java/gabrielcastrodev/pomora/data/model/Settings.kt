package gabrielcastrodev.pomora.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey(autoGenerate = false) val id: String,
    val timer: String,
    val pause: String
)
