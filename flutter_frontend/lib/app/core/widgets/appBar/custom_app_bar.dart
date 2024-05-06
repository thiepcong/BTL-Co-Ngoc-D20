import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';

import '../../values/app_colors.dart';

class CustomAppBar extends StatelessWidget implements PreferredSizeWidget {
  const CustomAppBar({
    super.key,
    this.isLeading = false,
    this.bgColor = AppColors.colorFFFFFFFF,
    required this.label,
    this.style,
    this.actions,
  });

  final Color bgColor;
  final String label;
  final TextStyle? style;
  final List<Widget>? actions;
  final bool isLeading;

  @override
  Widget build(BuildContext context) {
    return AppBar(
      centerTitle: true,
      automaticallyImplyLeading: false,
      leading: isLeading
          ? IconButton(
              onPressed: () {
                context.router.pop();
              },
              icon: const Icon(
                Icons.arrow_back,
                color: AppColors.colorFFFFFFFF,
              ))
          : null,
      backgroundColor: bgColor,
      title: Text(
        label,
        style: style,
      ),
      actions: actions,
    );
  }

  @override
  Size get preferredSize => const Size.fromHeight(kToolbarHeight);
}
