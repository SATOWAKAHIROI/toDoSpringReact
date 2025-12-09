# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## プロジェクト概要

ToDoリスト管理アプリケーション。Spring Boot (Java) バックエンドとReact (Vite) フロントエンドで構成されたフルスタックアプリケーション。

## 技術スタック

### バックエンド (`/backend`)
- Spring Boot 3.4.12
- Java 17
- Maven
- Spring Data JPA
- MySQL 8.0
- Spring Boot Actuator

### フロントエンド (`/frontend`)
- React 19.2.0
- Vite 7.2.4
- ESLint

### インフラ
- Docker Compose
- MySQL 8.0 (コンテナ)

## 開発コマンド

### Docker環境での起動
```bash
# アプリケーション全体を起動（バックエンド、フロントエンド、MySQL）
docker-compose up -d

# ログを表示
docker-compose logs -f

# 停止
docker-compose down
```

### バックエンド開発
```bash
cd backend

# ビルド
mvn clean package

# テスト実行
mvn test

# ローカル起動（MySQL接続先: localhost:3306）
mvn spring-boot:run
```

### フロントエンド開発
```bash
cd frontend

# 依存関係インストール
npm install

# 開発サーバー起動（ポート3000）
npm run dev

# ビルド
npm run build

# Lint実行
npm run lint
```

## アーキテクチャ

### バックエンド構造
```
com.example.backend/
├── controller/       # REST APIエンドポイント (UserController, TodoController)
├── entity/          # JPAエンティティ (User, Todo)
├── repository/      # Spring Data JPAリポジトリ
└── dto/
    ├── request/     # リクエストDTO (UserRequest, TodoRequest, LoginRequest)
    ├── response/    # レスポンスDTO (UserResponse, TodoResponse, AuthResponse)
    └── common/      # 共通DTO (ApiResponse)
```

### エンティティ関係
- **User**: システムユーザー（id, name, email, password, created_at, updated_at）
- **Todo**: TODOアイテム（id, title, completed, created_at, updated_at, created_by）
- UserとTodoは1対多の関係（User.id ← Todo.created_by）

### API共通レスポンス形式
全てのAPIレスポンスは`ApiResponse<T>`クラスでラップされる:
```json
{
  "success": true/false,
  "data": { ... },
  "error": {
    "code": "ERROR_CODE",
    "message": "エラーメッセージ",
    "details": { ... }
  }
}
```

### データベース接続
- Docker環境: `jdbc:mysql://db:3306/app_db`（環境変数で設定）
- ローカル開発: `jdbc:mysql://localhost:3306/app_db`（application.yml）
- ユーザー: `app_user` / パスワード: `app_pass`
- DDL自動更新: `update`モード

### ポート設定
- バックエンドAPI: 8080
- フロントエンド: 3000
- MySQL: 3306

## 開発時の注意点

### バックエンド
- エンティティは`@PrePersist`と`@PreUpdate`でタイムスタンプを自動設定
- TodoのUserリレーションは`FetchType.LAZY`で遅延ロード
- バリデーションは`spring-boot-starter-validation`を使用

### フロントエンド
- Viteを使用したビルドシステム
- ESLintでコード品質を維持
