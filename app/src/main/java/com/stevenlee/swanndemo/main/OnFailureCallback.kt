package com.stevenlee.swanndemo.main

/**
 * Used for displaying a SnackBar when there's a failure
 */
interface OnFailureCallback {
    fun onFailure(error: String)
}