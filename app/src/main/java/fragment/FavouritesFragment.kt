package fragment

import adapter.FavouritesRecyclerAdapter
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.internshala.bookhub.R
import database.BookDatabase
import database.BookEntity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavouritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


@Suppress("DEPRECATION")
class FavouritesFragment : Fragment() {
    lateinit var recyclerFavourite: RecyclerView
    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: FavouritesRecyclerAdapter
    var dbBookList = listOf<BookEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)
        recyclerFavourite = view.findViewById(R.id.recyclerFavourite)
        progressLayout = view.findViewById(R.id.progressLayout)
        progressBar = view.findViewById(R.id.progressBar)
        layoutManager = GridLayoutManager(activity as Context, 2)
        dbBookList = RetrieveFavourites(activity as Context).execute().get()

        if (dbBookList!=null&&activity != null) {
            progressLayout.visibility = View.GONE
            recyclerAdapter = FavouritesRecyclerAdapter(activity as Context, dbBookList)
            recyclerFavourite.adapter = recyclerAdapter
            recyclerFavourite.layoutManager = layoutManager
        }
        return view
    }

    class RetrieveFavourites(val context: Context) : AsyncTask<Void, Void, List<BookEntity>>() {
        override fun doInBackground(vararg p0: Void?): List<BookEntity> {
            val db = Room.databaseBuilder(context, BookDatabase::class.java, "books").build()
            return db.bookDao().getAllBooks()

        }
    }
}