package com.project.mvvmproject

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.project.mvvmproject.adaptors.QuotesAdaptor
import com.project.mvvmproject.databinding.ActivityMainBinding
import com.project.mvvmproject.viewModel.QuoteViewModel


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var quotesadaptor: QuotesAdaptor

    lateinit var templateView:TemplateView
    private var mInterstitialAd: InterstitialAd? = null

    lateinit var adLoader:AdLoader

    lateinit var viewModel: QuoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        load_nativeAd()

        viewModel = ViewModelProvider(this).get(QuoteViewModel::class.java)
        binding.showQuotesId.setOnClickListener {
            binding.proressId.visibility = View.VISIBLE
            binding.recyclerQuotes.visibility = View.GONE
            viewModel.showQuotes()
        }

        viewModel.quotesList.observe(this, Observer {
            quotesadaptor.setQuotesList(it)
            binding.proressId.visibility = View.GONE
            binding.recyclerQuotes.visibility = View.VISIBLE

        })

        binding.recyclerQuotes.layoutManager = LinearLayoutManager(this)
        quotesadaptor = QuotesAdaptor(this)
        binding.recyclerQuotes.adapter = quotesadaptor

        binding.btnShowAd.setOnClickListener {
            load_interstitial_ad()
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this)
                mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                    override fun onAdClicked() {
                        // Called when a click is recorded for an ad.
                        Log.d("TAG", "Ad was clicked.")
                    }

                    override fun onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        Log.d("TAG", "Ad dismissed fullscreen content.")
                        mInterstitialAd = null
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        // Called when ad fails to show.
                        Log.e("TAG", "Ad failed to show fullscreen content.")
                        mInterstitialAd = null
                    }

                    override fun onAdImpression() {
                        // Called when an impression is recorded for an ad.
                        Log.d("TAG", "Ad recorded an impression.")
                    }

                    override fun onAdShowedFullScreenContent() {
                        // Called when ad is shown.
                        Log.d("TAG", "Ad showed fullscreen content.")
                    }
                }
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.")
            }
        }

    }

    private fun load_nativeAd() {

        MobileAds.initialize(this)
        val adLoader = AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110")
            .forNativeAd(object : NativeAd.OnNativeAdLoadedListener {
                override fun onNativeAdLoaded(nativeAd: NativeAd) {
                    val styles = NativeTemplateStyle.Builder().build()
                    val template = findViewById<TemplateView>(R.id.ad_id)
                    template.setStyles(styles)
                    template.setNativeAd(nativeAd)
                }
            })
            .build()

        adLoader.loadAd(AdRequest.Builder().build())

    }

    private fun load_interstitial_ad(){
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("tag", adError.toString())
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d("TAG", "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })
    }

    override fun onBackPressed() {
// TODO Auto-generated method stub
        val builder = AlertDialog.Builder(this)
        // builder.setCancelable(false);
        builder.setTitle("MVVM Application")
        builder.setMessage("Do you want to Exit?")
        builder.setPositiveButton("yes") { dialog, which -> // TODO Auto-generated method stub
            finishAffinity()
        }
        builder.setNegativeButton(
            "No"
        ) { dialog, which -> // TODO Auto-generated method stub
            dialog.cancel()
        }

        val alert = builder.create()
        alert.show()
        //super.onBackPressed();
    }




}