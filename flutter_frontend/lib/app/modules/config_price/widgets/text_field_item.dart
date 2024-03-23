import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import '../../../core/models/price_scale.dart';
import '../../../core/values/app_colors.dart';

class TableRowItem extends TableRow {
  const TableRowItem(
    this.index,
    this.item, {
    this.onChangeStartIndex,
    this.onChangeEndIndex,
    this.onChangePrice,
    this.onDelete,
  });
  final int index;
  final PriceScale item;
  final Function(String e)? onChangeStartIndex;
  final Function(String e)? onChangeEndIndex;
  final Function(String e)? onChangePrice;
  final VoidCallback? onDelete;
  @override
  List<Widget> get children => [
        TableCell(
          child: CustomTextField(
            text: index + 1,
            enable: false,
          ),
        ),
        TableCell(
            child: CustomTextField(
          text: item.startIndex,
          enable: false,
          onChanged: (e) => onChangeStartIndex?.call(e),
        )),
        TableCell(
            child: CustomTextField(
          text: item.endIndex,
          onChanged: (e) => onChangeEndIndex?.call(e),
        )),
        TableCell(
            child: CustomTextField(
          text: item.price,
          onChanged: (e) => onChangePrice?.call(e),
        )),
        TableCell(
          child: IconButton(
            onPressed: () => onDelete?.call(),
            icon: const Icon(
              Icons.close,
              color: AppColors.colorFFB20000,
            ),
          ),
        ),
      ];
}

class CustomTextField extends StatefulWidget {
  const CustomTextField({
    super.key,
    this.text,
    this.onChanged,
    this.enable = true,
    this.isLast = false,
  });

  final int? text;
  final void Function(String)? onChanged;
  final bool enable;
  final bool isLast;

  @override
  State<CustomTextField> createState() => _CustomTextFieldState();
}

class _CustomTextFieldState extends State<CustomTextField> {
  final TextEditingController _controller = TextEditingController();

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    if (widget.text.toString() != _controller.text) {
      _controller.text =
          widget.isLast ? "Trở lên" : widget.text?.toString() ?? '0';
    }
    return TextField(
      readOnly: !widget.enable,
      onChanged: widget.onChanged,
      controller: _controller,
      inputFormatters: <TextInputFormatter>[
        FilteringTextInputFormatter.digitsOnly,
        LengthLimitingTextInputFormatter(5),
      ],
    );
  }
}
