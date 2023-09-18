package gabrielcastrodev.pomora.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import gabrielcastrodev.pomora.data.model.Settings
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: Settings)

    @Delete
    suspend fun deleteSettings(settings: Settings)

    @Query("SELECT * FROM settings")
    fun getSettings(): Flow<List<Settings>>
}