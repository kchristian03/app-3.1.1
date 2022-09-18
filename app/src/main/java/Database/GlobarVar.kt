package Database

import Model.Film

class GlobarVar {
    companion object {
        val STORAGERead_PERMISSION_CODE: Int = 2
        val STORAGEWrite_PERMISSION_CODE: Int = 3
        val listDataFilm = ArrayList<Film>()
    }
}