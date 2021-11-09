package com.conlage.smartshopping.view.components.main.permission

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState


@ExperimentalPermissionsApi
fun PermissionState.isPermanentlyDenied() =
    !shouldShowRationale && !hasPermission