package com.example.android4

class Setup {
    companion object {
        private var adapter: TodoAdapter? = null
        fun setAdapter(adapterArg: TodoAdapter) {
            adapter = adapterArg
        }
        fun getAdapter(): TodoAdapter {
            if (adapter == null) {
                val repo = DataRepository.getInstance()
                val data = repo.getData()
                adapter = TodoAdapter(data)
            }
            return adapter!!
        }
    }
}