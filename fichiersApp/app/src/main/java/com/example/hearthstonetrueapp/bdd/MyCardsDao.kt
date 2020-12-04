package com.example.hearthstonetrueapp.bdd

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MyCardsDao {
    @Query("SELECT * FROM mycards")
    fun getAll(): List<MyCards>

    @Query("SELECT * FROM mycards WHERE cardId IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<MyCards>
/*
    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User
*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg allUsersCards: MyCards)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertCard(cardsToSave: MyCards)

    @Delete
    fun delete(myCards: MyCards)
}