import 'package:flutter/material.dart';
import 'app_colors.dart';

final appTheme = ThemeData(
  primarySwatch: AppColors.colorPrimarySwatch,
  appBarTheme: const AppBarTheme(backgroundColor: AppColors.c359EC7_primary),
  visualDensity: VisualDensity.adaptivePlatformDensity,
  brightness: Brightness.light,
  primaryColor: AppColors.c359EC7_primary,
  colorScheme: const ColorScheme.light(
    background: AppColors.cFFFFFF_surface,
    surface: AppColors.cFFFFFF_surface,
  ),
  dividerColor: AppColors.cE4E4E4_divider,
  dialogTheme: const DialogTheme(
    shape: BeveledRectangleBorder(),
  ),
  floatingActionButtonTheme: FloatingActionButtonThemeData(
    sizeConstraints: BoxConstraints.tight(const Size(70, 70)),
  ),
);
