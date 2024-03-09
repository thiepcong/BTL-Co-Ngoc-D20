import 'package:flutter/material.dart';

import '/app/core/values/app_colors.dart';

abstract class FontW {
  static const small = FontWeight.w300;
  static const regular = FontWeight.w400;
  static const medium = FontWeight.w500;
  static const mediumM = FontWeight.w600;
  static const bold = FontWeight.w700;
}

abstract class TextStyles {
  static const _medium14_07 = TextStyle(
    fontFamily: "SVN",
    fontWeight: FontW.medium,
    fontSize: 14,
    letterSpacing: 0.7,
    color: AppColors.c1D1D1D_onSurface,
  );

  static const bold14TitleBold = _medium14_07;

  static TextStyle regularWhiteS20 = TextStyle(
    fontFamily: "SVN",
    fontSize: 20,
    color: AppColors.colorFFFFFFFF,
  );

  static TextStyle mediumMWhiteS20 = TextStyle(
    fontFamily: "SVN",
    fontSize: 20,
    color: AppColors.colorFFFFFFFF,
    fontWeight: FontW.mediumM,
  );

  static TextStyle score = TextStyle(
    fontFamily: "SVN",
    fontSize: 16,
    color: AppColors.colorFF590000,
    fontWeight: FontW.mediumM,
    backgroundColor: AppColors.colorFFFFFFFF,
  );

  static TextStyle mediumBlackS20 = TextStyle(
    fontFamily: "SVN",
    fontSize: 20,
    color: AppColors.colorFF000000,
    fontWeight: FontW.medium,
  );

  static TextStyle boldBlackS18 = TextStyle(
    fontFamily: "SVN",
    color: AppColors.colorFF000000,
    fontSize: 18,
    fontWeight: FontW.bold,
  );
  static TextStyle boldRedS18 = TextStyle(
    fontFamily: "SVN",
    color: AppColors.colorFFFF0000,
    fontSize: 18,
    fontWeight: FontW.bold,
  );

  static TextStyle size15 = TextStyle(
    fontFamily: "SVN",
    fontSize: 15,
  );

  static TextStyle size14 = TextStyle(
    fontFamily: "SVN",
    fontSize: 14,
  );

  static TextStyle size20 = TextStyle(
    fontFamily: "SVN",
    fontSize: 20,
  );

  static TextStyle mediumMBlackS18 = TextStyle(
    fontFamily: "SVN",
    fontWeight: FontW.mediumM,
    fontSize: 18,
  );

  static TextStyle regularMBlackS18 = TextStyle(
    fontFamily: "SVN",
    fontWeight: FontW.regular,
    fontSize: 18,
  );

  static TextStyle mediumMBlueS16 = TextStyle(
    fontFamily: "SVN",
    fontWeight: FontW.mediumM,
    fontSize: 16,
    color: AppColors.colorFF344054,
  );

  static TextStyle boldRedS20 = TextStyle(
    fontFamily: "SVN",
    color: AppColors.colorFF940000,
    fontSize: 20,
    fontWeight: FontW.bold,
  );

  static TextStyle boldRedS40 = TextStyle(
    fontFamily: "SVN",
    color: AppColors.colorFF9A0F0F,
    fontSize: 40,
    fontWeight: FontW.bold,
  );

  static TextStyle boldRed1S18 = TextStyle(
    fontFamily: "SVN",
    color: AppColors.colorFF9A0F0F,
    fontSize: 18,
    fontWeight: FontWeight.bold,
  );

  static TextStyle regularBlueS18 = TextStyle(
    fontFamily: "SVN",
    color: AppColors.colorFF2F394B,
    fontSize: 18,
  );

  static TextStyle mediumRedS20 = TextStyle(
    fontFamily: "SVN",
    fontWeight: FontW.medium,
    fontSize: 20,
    color: AppColors.colorFFB20000,
  );

  static TextStyle mediumWhiteS14 = TextStyle(
    fontFamily: "SVN",
    fontSize: 14,
    fontWeight: FontW.medium,
    color: AppColors.colorFFFFFFFF,
  );

  static TextStyle mediumWhitesS14 = TextStyle(
    fontFamily: "SVN",
    fontSize: 14,
    fontWeight: FontW.medium,
    color: AppColors.colorFFBBBABA,
  );

  static TextStyle regularWhiteS10 = TextStyle(
    fontFamily: "SVN",
    fontSize: 10,
    fontWeight: FontW.regular,
    color: AppColors.colorFFFFFFFF,
  );

  static TextStyle regularWhiteS12 = TextStyle(
    fontFamily: "SVN",
    fontSize: 12,
    fontWeight: FontW.regular,
    color: AppColors.colorFFFFFFFF,
  );

  static TextStyle boldBlackS10 = TextStyle(
    fontFamily: "SVN",
    fontSize: 10,
    fontWeight: FontW.bold,
    color: AppColors.colorFF000000,
  );

  static TextStyle mediumRedS14 = TextStyle(
    fontFamily: "SVN",
    fontSize: 14,
    fontWeight: FontW.medium,
    color: AppColors.colorFF940000,
  );

  static TextStyle mediumBlackkS14 = TextStyle(
    fontFamily: "SVN",
    fontSize: 14,
    fontWeight: FontW.medium,
    color: AppColors.colorFF000000,
  );

  static TextStyle regularRedS13 = TextStyle(
    fontFamily: "SVN",
    fontSize: 13,
    fontWeight: FontW.regular,
    color: AppColors.colorFFFF0000,
  );

  static TextStyle mediumWhiteS36 = TextStyle(
    fontFamily: "SVN",
    fontWeight: FontW.medium,
    fontSize: 36,
    color: AppColors.colorFFFFFFFF,
  );

  static TextStyle regularBlackS14 = TextStyle(
    fontFamily: "SVN",
    fontWeight: FontW.regular,
    fontSize: 14,
    color: AppColors.colorFF424242,
  );

  static TextStyle regularRedS14 = TextStyle(
    fontFamily: "SVN",
    fontWeight: FontW.regular,
    fontSize: 14,
    color: AppColors.colorFF940000,
  );

  static TextStyle mediumBlackS42 = TextStyle(
    fontFamily: "SVN",
    fontWeight: FontW.medium,
    fontSize: 36,
    height: 11 / 9,
    color: AppColors.colorFF000000,
  );

  static TextStyle smallBlackS24 = TextStyle(
    fontFamily: "SVN",
    fontWeight: FontW.small,
    fontSize: 24,
    color: AppColors.colorFF000000,
  );

  static TextStyle regularBlackS16 = TextStyle(
    fontFamily: "SVN",
    fontWeight: FontW.regular,
    fontSize: 16,
    color: AppColors.colorFF000000,
  );

  static TextStyle regularRedS16 = TextStyle(
    fontFamily: "SVN",
    fontWeight: FontW.regular,
    fontSize: 16,
    color: AppColors.colorFF9A0F0F,
  );

  static TextStyle boldBlackS16 = TextStyle(
    fontFamily: "SVN",
    color: AppColors.colorFF313131,
    fontSize: 16,
    fontWeight: FontW.bold,
  );

  static TextStyle mediumWhiteS16 = TextStyle(
    fontFamily: "SVN",
    color: AppColors.colorFFFFFFFF,
    fontSize: 16,
    fontWeight: FontW.medium,
  );

  static TextStyle mediumMWhiteS24 = TextStyle(
    fontFamily: "SVN",
    color: AppColors.colorFFFFFFFF,
    fontSize: 24,
    fontWeight: FontW.mediumM,
  );

  static TextStyle regularWhiteS16 = TextStyle(
    fontFamily: "SVN",
    color: AppColors.colorFFFFFFFF,
    fontSize: 16,
    fontWeight: FontWeight.w400,
  );

  static TextStyle regularWhiteS18 = TextStyle(
    fontFamily: "SVN",
    color: AppColors.colorFFFFFFFF,
    fontSize: 18,
    fontWeight: FontWeight.w400,
  );

  static TextStyle boldWhiteS20 = TextStyle(
    fontFamily: "SVN",
    color: AppColors.colorFFFFFFFF,
    fontSize: 20,
    fontWeight: FontW.bold,
  );

  static TextStyle mediumMWhiteS18 = TextStyle(
    fontFamily: "SVN",
    color: AppColors.colorFFFFFFFF,
    fontSize: 18,
    fontWeight: FontW.mediumM,
  );

  static TextStyle boldWhiteS28 = TextStyle(
    fontFamily: "SVN",
    color: AppColors.colorFFFFFFFF,
    fontSize: 28,
    fontWeight: FontW.bold,
  );

  static TextStyle boldBlackS28 = TextStyle(
    fontFamily: "SVN",
    color: AppColors.colorFF000000,
    fontSize: 28,
    fontWeight: FontW.bold,
  );

  static TextStyle boldWhiteS24 = TextStyle(
    fontFamily: "SVN",
    color: AppColors.colorFFFFFFFF,
    fontSize: 24,
    fontWeight: FontW.bold,
  );

  static TextStyle boldBlackS20 = TextStyle(
    fontFamily: "SVN",
    color: AppColors.colorFF000000,
    fontSize: 20,
    fontWeight: FontW.bold,
  );

  static final mediumBlackS14 = TextStyle(
    fontFamily: "SVN",
    color: AppColors.colorFF313131.withOpacity(0.5),
    fontSize: 14,
    fontWeight: FontW.medium,
  );

  static TextStyle greyS14 = TextStyle(
    fontFamily: "SVN",
    color: AppColors.colorFF636363,
    fontSize: 14,
  );

  static TextStyle regularGrayS14 = TextStyle(
    fontFamily: "SVN",
    color: AppColors.colorFF475467,
    fontSize: 14,
    fontWeight: FontW.regular,
  );
}
