package com.apex.codeassesment.data.local

import com.apex.codeassesment.data.model.Name
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.model.local.LocalDataSource
import com.google.gson.Gson
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*


class LocalDataSourceTest {

    private lateinit var prefs: PreferencesHelper
    private lateinit var gson: Gson

    private lateinit var localDataSource: LocalDataSource

    @Before
    fun initMocks() {
        //This will initialize the annotated mocks
        prefs = Mockito.mock(PreferencesHelper::class.java)
        gson = Mockito.mock(Gson::class.java)
        localDataSource = LocalDataSource(prefs, gson)
    }

    @Test
    fun `loadUser() with valid data`() {
        // Arrange { "first": "John"}
        val userJson = "{ \"name\": { \"first\": \"John\"} , \"gender\": \"male\" }"
        val expectedUser = User(name= Name(first = "John"), gender =  "male")
        `when`(prefs.loadUser()).thenReturn(userJson)
        `when`(gson.fromJson(eq(userJson), eq(User::class.java))).thenReturn(expectedUser)

        // Act
        val result = localDataSource.loadUser()

        // Assert
        assertEquals(expectedUser, result)
        verify(prefs).loadUser()
        verify(gson).fromJson(eq(userJson), eq(User::class.java))
    }

    @Test
    fun `loadUser() with null data`() {

        // Act
        val result = localDataSource.loadUser()

        // Assert
        assertEquals(null, result)
        verify(prefs).loadUser()
    }

    @Test
    fun `saveUser() with valid user`() {
        // Arrange
        val user = User("Alice", gender = "30")
        val userJson = "{ \"name\": \"Alice\", \"gender\": 30 }"

        // Act
        localDataSource.saveUser(user)

        // Assert
        verify(gson).toJson(eq(user))
        verify(prefs).saveUser(eq(userJson))
    }
}