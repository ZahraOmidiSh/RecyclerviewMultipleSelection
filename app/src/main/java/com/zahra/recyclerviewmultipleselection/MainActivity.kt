package com.zahra.recyclerviewmultipleselection

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.switchmaterial.SwitchMaterial
import com.zahra.recyclerviewmultipleselection.adapter.Adapter
import com.zahra.recyclerviewmultipleselection.adapter.item.ItemData
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private lateinit var switchMaterial: SwitchMaterial
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    private lateinit var layoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findView()
        setListener()
        initList()
        reload()

    }
    private fun findView(){
        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout)
        switchMaterial=findViewById(R.id.switchMaterial)
        recyclerView=findViewById(R.id.recyclerView)
    }

    private fun setListener(){
        switchMaterial.setOnCheckedChangeListener{_,isChecked ->
            adapter.toggleSelectionMode((isChecked))

        }
    }

    private fun initList(){
        layoutManager= LinearLayoutManager(this)
        adapter = Adapter()
        adapter.onLoadMore = {
            loadMore()
        }

        swipeRefreshLayout.setOnRefreshListener {
            reload()
            swipeRefreshLayout.isRefreshing=false
        }

        recyclerView.layoutManager =layoutManager
        recyclerView.adapter=adapter

    }

    private fun reload(){
        recyclerView.post{
            adapter.reload(createDummyData(0,15))
        }
    }

    private fun loadMore(){
        recyclerView.post{
            adapter.loadMore(createDummyData(adapter.itemCount,10))
        }

    }

    private fun createDummyData(offset: Int, limit: Int): MutableList<ItemData> {
        val list = mutableListOf<ItemData>()

        var data: ItemData
        for (i in offset..(offset + limit)) {
            data = ItemData(
                id = UUID.randomUUID().toString().replace("-", ""),
                content = "content index $i"
            )
            list.add(data)
        }
        return list
    }
}