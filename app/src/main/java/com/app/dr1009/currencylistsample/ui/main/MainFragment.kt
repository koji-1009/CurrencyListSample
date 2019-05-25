package com.app.dr1009.currencylistsample.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.dr1009.currencylistsample.R
import com.app.dr1009.currencylistsample.databinding.ListItemCurrencyBinding
import com.app.dr1009.currencylistsample.databinding.MainFragmentBinding
import com.app.dr1009.currencylistsample.entity.Currency
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class MainFragment : DaggerFragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: MainFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = CurrencyListAdapter(viewModel::setCurrency)
        binding.recyclerView.adapter = adapter
        viewModel.currencyList.observe(this, Observer(adapter::submitList))

        viewModel.networkError.observe(this, Observer {
            if (it == null) return@Observer

            val errorMessage = getString(
                when (it) {
                    is IOException, is TimeoutException -> R.string.error_network
                    else -> R.string.error_cmn
                }
            )
            Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
        })

        return binding.root
    }

    internal class CurrencyListAdapter(
        private val f: (Currency) -> Unit
    ) : ListAdapter<Currency, CurrencyListAdapter.CurrencyViewHolder>(
        object : DiffUtil.ItemCallback<Currency>() {
            override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean = oldItem.pair == newItem.pair
            override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean = oldItem == newItem
        }
    ) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
            return CurrencyViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.list_item_currency, parent, false)
            )
        }

        override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
            holder.bindTo(getItem(position), f)
        }

        internal class CurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private val binding = ListItemCurrencyBinding.bind(view)

            fun bindTo(currency: Currency, f: (Currency) -> Unit) {
                binding.currency = currency
                binding.layoutCurrency.setOnClickListener { f(currency) }
                binding.executePendingBindings()
            }
        }
    }
}
