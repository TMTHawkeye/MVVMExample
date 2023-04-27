package com.project.mvvmproject.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.project.mvvmproject.model.CatsFacts
import com.project.mvvmproject.repository.QuoteRepository

class QuoteViewModel(application:Application):AndroidViewModel(application) {

    val repository=QuoteRepository(application)
    val quotesList : LiveData<List<CatsFacts>>

    init {
        this.quotesList=repository.quotesList
    }

    fun showQuotes(){
        repository.showQuotes()
    }

}