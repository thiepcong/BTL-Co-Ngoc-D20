import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../cubit/config_price_cubit.dart';
import '../cubit/config_price_state.dart';
import '../repository/config_price_repository.dart';

class ConfigPriceView extends StatefulWidget {
  const ConfigPriceView({super.key});

  @override
  State<ConfigPriceView> createState() => _ConfigPriceViewState();
}

class _ConfigPriceViewState extends State<ConfigPriceView> {
  List<TableRow> rows = [
    const TableRow(children: [
      TableCell(child: Text('STT')),
      TableCell(child: Text('Chỉ số đầu')),
      TableCell(child: Text('Chỉ số cuối')),
      TableCell(child: Text('Đơn giá')),
      TableCell(child: Text('Khác')),
    ]),
    const TableRow(children: [
      TableCell(child: Text('1')),
      TableCell(child: Text('0')),
      TableCell(child: Text('10')),
      TableCell(child: Text('10000')),
      TableCell(child: Text('X')),
    ]),
  ];

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (_) => ConfigPriceCubit(context.read<ConfigPriceRepository>()),
      child: _buildPage(context),
    );
  }

  Widget _buildPage(BuildContext context) {
    return BlocBuilder<ConfigPriceCubit, ConfigPriceState>(
      builder: (context, state) {
        return Scaffold(
          appBar: AppBar(
            automaticallyImplyLeading: false,
            title: const Text('Cấu hình giá nước'),
          ),
          body: DefaultTabController(
            length: 5,
            child: Column(
              children: [
                const TabBar(
                  tabs: [
                    Tab(text: 'Hộ nghèo'),
                    Tab(text: 'Cơ quan hành chính'),
                    Tab(text: 'Hoạt động sản xuất'),
                    Tab(text: 'Kinh doanh dịch vụ'),
                    Tab(text: 'Hộ cư dân khác'),
                  ],
                ),
                Expanded(
                  child: TabBarView(
                    children: [
                      SingleChildScrollView(
                        child: Column(
                          children: [
                            const SizedBox(height: 10),
                            ElevatedButton(
                              onPressed: () {
                                setState(() {
                                  rows.add(TableRow(children: [
                                    TableCell(child: Text('${rows.length}')),
                                    const TableCell(child: TextField()),
                                    const TableCell(child: TextField()),
                                    const TableCell(child: TextField()),
                                    const TableCell(child: TextField()),
                                  ]));
                                });
                              },
                              child: const Text('Thêm bậc'),
                            ),
                            const SizedBox(height: 10),
                            Table(
                              border: TableBorder.all(),
                              children: rows,
                            ),
                            const SizedBox(height: 10),
                            ElevatedButton(
                              onPressed: () {
                                // Xử lý lưu thông tin
                                showDialog(
                                  context: context,
                                  builder: (BuildContext context) {
                                    return AlertDialog(
                                      title: const Text('Thông báo'),
                                      content:
                                          const Text('Thông tin đã được lưu!'),
                                      actions: [
                                        TextButton(
                                          onPressed: () {
                                            Navigator.of(context).pop();
                                          },
                                          child: const Text('OK'),
                                        ),
                                      ],
                                    );
                                  },
                                );
                              },
                              child: const Text('Lưu'),
                            ),
                          ],
                        ),
                      ),
                      const Center(child: Text('Cơ quan hành chính')),
                      const Center(child: Text('Hoạt động sản xuất')),
                      const Center(child: Text('Kinh doanh dịch vụ')),
                      const Center(child: Text('Hộ cư dân khác')),
                    ],
                  ),
                ),
              ],
            ),
          ),
        );
      },
    );
  }
}
