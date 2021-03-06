package com.maxm.sorts.activities.main

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.widget.ImageButton
import android.widget.Toast
import com.maxm.sorts.data.Algorithm
import com.maxm.sorts.fragments.CustomFragmentPageAdapter
import com.maxm.sorts.fragments.FragmentAlgorithmDescription
import com.maxm.sorts.fragments.code.FragmentCode
import com.maxm.sorts.R
import com.maxm.sorts.views.WhiteGreyAccentToolbar
import kotlin.system.exitProcess

/**
 * This class represents and implements the logic of main layout.activity_main
 * @author MaxMirren
 */
class ActivityMain : AppCompatActivity() {

    private lateinit var mainPresenter: MainPresenter
    private lateinit var viewPager: ViewPager
    private lateinit var actionBarDrawerToggle : ActionBarDrawerToggle
    private lateinit var fragmentAlgorithmDescription: FragmentAlgorithmDescription
    private lateinit var fragmentCode: FragmentCode
    private lateinit var imgBtnCodeDesc: ImageButton
    private lateinit var bottomAppBar: WhiteGreyAccentToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    override fun onBackPressed() {
        finish()
        exitProcess(0)
    }

    /**
     * Uses as menu to start up class init processes
     */
    private fun initialize() {
        initializeGloballyUsedViews()
        mainPresenter = MainPresenter(this)
        setImgBtnCodeDescBehaviour()
        mainPresenter.initializeAlgorithmsList()
        setViewPager()
        setNavigationView()
        setFabBehaviour()
        setImgBtnShareBehaviour()
        setBottomNavigationView()
    }

    /**
     * Initializes globally used views - connects them to pre-declared lateinit variables
     */
    private fun initializeGloballyUsedViews() {
        viewPager = findViewById(R.id.a_m_view_pager)
        imgBtnCodeDesc = findViewById(R.id.a_m_bab_img_btn_code_desc)
    }

    /**
     * Sets image button with id a_m_bab_img_btn_code_desc behaviour
     */
    private fun setImgBtnCodeDescBehaviour() {
        imgBtnCodeDesc.setOnClickListener {viewPager.currentItem = 1}
    }

    /**
     * Sets view pager id.a_m_view_pager controls
     */
    private fun setViewPager() {
        val customFragmentPageAdapter = CustomFragmentPageAdapter(supportFragmentManager!!)
        fragmentAlgorithmDescription = FragmentAlgorithmDescription()
        fragmentCode = FragmentCode()
        bottomAppBar = this@ActivityMain.findViewById(R.id.a_m_bottom_app_bar)
        fragmentCode.setMainToolbar(bottomAppBar)
        customFragmentPageAdapter.setFragmentList(arrayListOf(fragmentAlgorithmDescription, fragmentCode))
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    bottomAppBar.setBottomAppBarBackgroundWhite(true)
                    imgBtnCodeDesc.background = getDrawable(R.drawable.baseline_code_white_48dp)
                    imgBtnCodeDesc.setOnClickListener {viewPager.currentItem = 1}
                }
                else {
                    bottomAppBar.setBottomAppBarBackgroundWhite(false)
                    imgBtnCodeDesc.background = getDrawable(R.drawable.baseline_description_white_48dp)
                    imgBtnCodeDesc.setOnClickListener {viewPager.currentItem = 0}
                }
            }

        })
        viewPager.adapter = customFragmentPageAdapter
        viewPager.currentItem = 0
    }

    /**
     * Sets navigation view with a_m_nav behaviour
     */
    private fun setNavigationView() {
        val navigationView: NavigationView = findViewById(R.id.a_m_nav)
        mainPresenter.addAllItemsToNavigationView(navigationView)
        navigationView.setNavigationItemSelectedListener { it ->
            run {
                val algorithmName = Algorithm.List.getFieldOfAlgorithmWithIndex(it.itemId, Algorithm.List.Fields.NAME)
                val algorithmDescription =
                    Algorithm.List.getFieldOfAlgorithmWithIndex(it.itemId, Algorithm.List.Fields.DESCRIPTION)
                val algorithmDebugger =
                    Algorithm.List.getFieldOfAlgorithmWithIndex(it.itemId, Algorithm.List.Fields.DEBUGGER)
                fragmentAlgorithmDescription.setContent(algorithmName, algorithmDescription)
                fragmentCode.setContent(algorithmName, algorithmDebugger)
                val drawerLayout: DrawerLayout = this@ActivityMain.findViewById(R.id.a_m_lyt_drl)
                drawerLayout.closeDrawer(Gravity.START)
            }
            false
        }
    }

    /**
     * Sets floating action button id.desc_a_m_img_btn_go behaviour
     */
    private fun setFabBehaviour() {
        val fab: ImageButton = findViewById(R.id.desc_a_m_img_btn_go)
        fab.setOnClickListener {
            viewPager.currentItem = 1
        }
    }

    /**
     * Sets ImgBtn Share (placed on bottom_app_bar.xml) behaviour
     */
    private fun setImgBtnShareBehaviour() {
        val imgBtnCode: ImageButton = findViewById(R.id.a_m_bab_img_btn_share)
        imgBtnCode.setOnClickListener {
            Toast.makeText(this, "This function is not implemented yet", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Sets navigation drawer availability and content
     */
    private fun setBottomNavigationView() {
        val drawerLayout: DrawerLayout = findViewById(R.id.a_m_lyt_drl)
        val toolbar: Toolbar = findViewById(R.id.a_m_bottom_app_bar)
        setSupportActionBar(toolbar)
        actionBarDrawerToggle =
                ActionBarDrawerToggle(
                    this, drawerLayout, toolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close
                )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }

}
