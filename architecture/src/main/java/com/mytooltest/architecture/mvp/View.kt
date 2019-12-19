package com.mytooltest.architecture.mvp


interface IView {
    fun setPresenter(presenter: IPresenter)
    fun loading()
    fun showData(data: String)
}
