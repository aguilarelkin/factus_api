package com.factus.app.ui.navigation

sealed class RouteFactus(val route: String) {
    data object Home : RouteFactus("home")
    data object Login : RouteFactus("login")
    data object Facture : RouteFactus("facture")
}