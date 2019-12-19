package com.mytooltest.architecture.mvc


class HandleController : IController {
    private var model: IModel? = null

    override fun onDataChanged(data: String) {
        model?.handleData(data)
    }

    override fun clearData() {
        model?.clearData()
    }

    override fun setModel(model: IModel) {
    }
}

interface IController {
    fun setModel(model: IModel)
    fun onDataChanged(data: String)
    fun clearData()
}