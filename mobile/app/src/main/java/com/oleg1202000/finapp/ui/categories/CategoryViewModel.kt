package com.oleg1202000.finapp.ui.categories

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.oleg1202000.finapp.data.*
import com.oleg1202000.finapp.di.RepositoryModule
import dagger.hilt.android.internal.modules.ApplicationContextModule
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


/*
data class CategoryUiState (
    val category : List<Categories> = listOf(),
    var errorMessage : String? = null,
)


@HiltViewModel
class CategoryViewModel

@Inject constructor(
    private val repository: RepositoryModule
)
    : ViewModel() {

    */
/*object Dependencies {

        private lateinit var applicationContext: Context

        fun init(context: Context) {
            applicationContext = context
        }

        val db: FinappDatabase = Room.databaseBuilder(
        applicationContext, FinappDatabase::class.java, "finappdatabase.db" // Caused by: kotlin.UninitializedPropertyAccessException: lateinit property applicationContext has not been initialized
        )
        .addTypeConverter(DateConverter::class)
        .build()
    } *//*


    // var CategoryDao = db.categoriesDao()

    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()


    */
/*init {
        viewModelScope.launch {
            flow { emit(CategoryDao.getCategories())}.flowOn(Dispatchers.IO)

                .catch { throwable ->
                    throw throwable
                }
                .collect {value ->
                _uiState.value = CategoryUiState(category = value)
            }
        }
    }*//*



    init {
        viewModelScope.launch {
            repository.getAllCategries()
                .catch { throwable ->
                    throw throwable
                }
                .collect {value ->
                _uiState.value = CategoryUiState(category = value)
            }
        }
    }

     // val category : List<FakeCategories> = listOf(),
    //fun setCategory(category : Categories) {
  //      repository.setCategory(category)
 //   }


}
*/

