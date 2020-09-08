package com.example.myapplication.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.myapplication.model.News
import com.example.myapplication.model.ShopOwners
import com.example.myapplication.network.NetworkService
import com.example.myapplication.network.NetworkServiceForShoplist
import com.example.myapplication.utill.State
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class ShopOwnerListPagination(
    private val networkService: NetworkServiceForShoplist,
    private val compositeDisposables: CompositeDisposable
) : PageKeyedDataSource<Int, ShopOwners>() {

    var state: MutableLiveData<State> = MutableLiveData()
    private var retryCompletable: Completable? = null
    var authtoken =
        "jwt eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwidXNlclR5cGUiOiJTVVBFUkFETUlOIiwiaWF0IjoxNTg2MjU4NTA4fQ.PfjEdI53hsOf1ihk8bNeelR9tV6DTGVhsF1vZNCe5BU"




    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposables.add(
                retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()

            )
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ShopOwners>) {
       updateState(State.LOADING)
       /* compositeDisposables.add(


           networkService.getShopDetails(authtoken,params.key)

               .subscribe(
                   { response ->
                       updateState(State.DONE)
                       callback.onResult(response.news,
                           params.key + 1
                       )
                   },
                   {
                       updateState(State.ERROR)
                       setRetry(Action { loadAfter(params, callback) })
                   }
               )
        )*/

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ShopOwners>) {
        TODO("Not yet implemented")

    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ShopOwners>
    ) {
        updateState(State.LOADING)
       // compositeDisposables.add(
            //networkService.getShopDetails(authtoken,1)


               /* .subscribe(
                    { response ->
                        updateState(State.DONE)
                        callback.onResult(response.shopowner,
                            null,
                            2
                        )
                    },
                    {
                        updateState(State.ERROR)
                        setRetry(Action { loadInitial(params, callback) })
                    }
                )
        )*/

    }
}


