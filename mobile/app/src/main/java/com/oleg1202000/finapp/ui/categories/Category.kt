package com.oleg1202000.finapp.ui.categories

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.oleg1202000.finapp.data.Categories
import com.oleg1202000.finapp.di.RepositoryModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.reflect.InvocationTargetException
import javax.inject.Inject


@Composable
fun CategoryScreen(
    viewModel : CategoryViewModel = viewModel()
) {

    //Log.d("err", getStackString)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Log.d("test_message", "Успешно")
    Text(text = "fdfd")
        var catTest = uiState.category[0].id
    LazyColumn()
    {


        item {
            Text(text = catTest.toString())
        }
    }

}


// TODO: !!!!

//test
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
    } */

    // var CategoryDao = db.categoriesDao()

    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()


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
    }*/


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


@Preview
@Composable
fun CategoryPreview() {
    CategoryScreen()
}
