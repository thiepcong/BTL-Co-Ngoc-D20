import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/app/core/values/show_message_internal.dart';
import 'package:flutter_frontend/app/main_router.dart';

import '../../../core/values/app_colors.dart';
import '../../../core/values/text_styles.dart';
import '../../../core/widgets/appBar/custom_app_bar.dart';
import '../cubit/login_cubit.dart';
import '../cubit/login_state.dart';
import '../repository/login_repository.dart';

class LoginView extends StatefulWidget {
  const LoginView({super.key});

  @override
  State<LoginView> createState() => _LoginViewState();
}

class _LoginViewState extends State<LoginView> {
  final TextEditingController _usernameController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    return Title(
      color: AppColors.colorFFFFFFFF,
      title: 'Đăng nhập',
      child: BlocProvider(
        create: (_) => LoginCubit(context.read<LoginRepository>()),
        child: _buildPage(context),
      ),
    );
  }

  Widget _buildPage(BuildContext context) {
    return MultiBlocListener(
      listeners: [
        BlocListener<LoginCubit, LoginState>(
          listenWhen: (previous, current) =>
              previous.authDone != current.authDone,
          listener: (context, state) {
            if (state.authDone) {
              context.pushRoute(const ManagerViewRoute());
            }
          },
        ),
        BlocListener<LoginCubit, LoginState>(
          listenWhen: (previous, current) =>
              previous.message != current.message,
          listener: (context, state) {
            if (state.message != null) {
              ShowMessageInternal.showOverlay(context, state.message!);
            }
          },
        ),
      ],
      child: BlocBuilder<LoginCubit, LoginState>(
        builder: (context, state) {
          final cubit = context.read<LoginCubit>();
          return Scaffold(
            appBar: const CustomAppBar(
              label: 'Đăng nhập',
              bgColor: AppColors.colorFF940000,
              style: TextStyles.boldWhiteS20,
            ),
            body: Stack(
              children: [
                Center(
                  child: Container(
                    decoration: BoxDecoration(
                      border: Border.all(),
                      borderRadius: const BorderRadius.all(Radius.circular(12)),
                    ),
                    padding: const EdgeInsets.symmetric(horizontal: 24),
                    width: 400,
                    height: 500,
                    child: Form(
                      key: _formKey,
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          const Text(
                            "Đăng nhập",
                            style: TextStyles.boldBlackS28,
                          ),
                          TextFormField(
                            controller: _usernameController,
                            decoration: const InputDecoration(
                              labelText: 'Tên người dùng',
                              border: OutlineInputBorder(),
                            ),
                            onFieldSubmitted: (e) {
                              if (_formKey.currentState!.validate()) {
                                cubit.login(_usernameController.text,
                                    _passwordController.text);
                              }
                            },
                            validator: (value) {
                              if (value == null || value.trim().isEmpty) {
                                return 'Vui lòng nhập tên người dùng!';
                              }
                              return null;
                            },
                          ),
                          const SizedBox(height: 16),
                          TextFormField(
                            controller: _passwordController,
                            obscureText: true,
                            decoration: const InputDecoration(
                              labelText: 'Mật khẩu',
                              border: OutlineInputBorder(),
                            ),
                            onFieldSubmitted: (e) {
                              if (_formKey.currentState!.validate()) {
                                cubit.login(_usernameController.text,
                                    _passwordController.text);
                              }
                            },
                            validator: (value) {
                              if (value == null || value.trim().isEmpty) {
                                return 'Vui lòng nhập mật khẩu!';
                              }
                              return null;
                            },
                          ),
                          const SizedBox(height: 16),
                          ElevatedButton(
                            onPressed: () {
                              if (_formKey.currentState!.validate()) {
                                cubit.login(_usernameController.text,
                                    _passwordController.text);
                              }
                            },
                            child: const Text('Login'),
                          ),
                        ],
                      ),
                    ),
                  ),
                ),
                state.isLoading
                    ? Container(
                        color: AppColors.colorFF000000.withOpacity(0.5),
                        alignment: Alignment.center,
                        child: const CircularProgressIndicator(),
                      )
                    : const SizedBox.shrink()
              ],
            ),
          );
        },
      ),
    );
  }
}
