package com.mytooltest.architecture.mvc


interface IView {
    fun setController(controller: IController)
    fun dataHanding()
    fun onDataHandled(data: String)
}
