import 'package:flutter/material.dart';

import '../../values/text_styles.dart';

class CustomModal extends Dialog {
  final String message;
  final Widget content;
  final List<Widget> actions;
  final TextStyle? messageTextStyle;
  final EdgeInsetsGeometry? padding;

  CustomModal({
    super.key,
    required this.message,
    required this.content,
    required this.actions,
    this.messageTextStyle,
    this.padding,
  }) : super(
          child: Padding(
            padding: padding ??
                const EdgeInsets.symmetric(horizontal: 15, vertical: 30),
            child: Column(
              mainAxisSize: MainAxisSize.min,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Text(message,
                    style: messageTextStyle ?? TextStyles.bold14TitleBold,
                    textAlign: TextAlign.center),
                const SizedBox(
                  height: 30,
                ),
                content,
                const SizedBox(
                  height: 50,
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: actions,
                ),
              ],
            ),
          ),
        );
}

class ForceLogoutDialog extends AlertDialog {
  final VoidCallback loginCallback;

  final String titleDialog;
  final String body;
  ForceLogoutDialog(
      {super.key,
      required this.loginCallback,
      this.titleDialog = '',
      this.body = '他の端末からログインがありました。'})
      : super(
          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              if (titleDialog.isNotEmpty) ...[
                Text(
                  titleDialog,
                  style: TextStyles.bold14TitleBold
                      .copyWith(fontWeight: FontWeight.bold),
                  textAlign: TextAlign.center,
                ),
                const SizedBox(
                  height: 16,
                ),
              ],
              Text(
                body,
                style: TextStyles.bold14TitleBold,
                textAlign: TextAlign.center,
              ),
              const SizedBox(
                height: 16,
              ),
              // PrimaryButton(
              //   title: 'ログインへ',
              //   buttonSize: ButtonSize.small,
              //   onTap: () => loginCallback(),
              // ),
            ],
          ),
        );
}

class ErrorDialog extends AlertDialog {
  final String message;
  final VoidCallback callback;

  ErrorDialog(this.message, this.callback, {super.key})
      : super(
          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              Text(
                'エラー',
                style: TextStyles.bold14TitleBold
                    .copyWith(fontWeight: FontWeight.bold),
                textAlign: TextAlign.center,
              ),
              const SizedBox(
                height: 16,
              ),
              Text(
                message,
                style: TextStyles.bold14TitleBold,
                textAlign: TextAlign.center,
              ),
              const SizedBox(height: 16),
              // PrimaryButton(
              //   title: 'キャンセル',
              //   buttonSize: ButtonSize.small,
              //   onTap: () => callback(),
              // ),
            ],
          ),
        );
}
