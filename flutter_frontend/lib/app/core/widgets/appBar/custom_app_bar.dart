import 'package:flutter/material.dart';

import '../../values/app_colors.dart';

class CustomAppBar extends StatelessWidget implements PreferredSizeWidget {
  const CustomAppBar({
    super.key,
    this.bgColor = AppColors.colorFFFFFFFF,
    required this.label,
    this.style,
  });

  final Color bgColor;
  final String label;
  final TextStyle? style;

  @override
  Widget build(BuildContext context) {
    return AppBar(
      centerTitle: true,
      automaticallyImplyLeading: false,
      backgroundColor: bgColor,
      title: Text(
        label,
        style: style,
      ),
    );
  }

  @override
  Size get preferredSize => const Size.fromHeight(kToolbarHeight);
}
