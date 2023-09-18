package gabrielcastrodev.pomora.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import gabrielcastrodev.pomora.data.model.Settings

@Database(entities = [Settings::class], version = 1)
abstract class SettingsDatabase : RoomDatabase() {
    abstract val dao : SettingsDao
}