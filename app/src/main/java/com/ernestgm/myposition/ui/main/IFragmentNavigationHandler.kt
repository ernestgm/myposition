package com.ernestgm.myposition.ui.main

import android.os.Bundle

interface IFragmentNavigationHandler {
    fun goToFragment(actionNavigationId: Int, bundle: Bundle? = null)
}