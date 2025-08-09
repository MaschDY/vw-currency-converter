# VW Currency Converter

## Descrição do projeto

**VW Currency Converter** é um aplicativo Android desenvolvido em Kotlin que permite converter valores entre moedas, utilizando a API do ExchangeRate Host. O app oferece suporte a modo escuro/claro, histórico de conversões, uma interface com Jetpack Compose e arquiteture MVVM + Clean Architecture.

## Passos para rodar o app

### Requisitos
- Android Studio Arctic Fox ou superior
- JDK 11 ou superior
- SDK do Android (min. API 24)
- Emulador ou Android físico

### Instalação
1. **Clone o repositório:**
   ```bash
   git clone https://github.com/MaschDY/vw-currency-converter.git
   ```

2. **Abra o projeto no Android Studio**

3. **Configuração da API**
    1. **Obtenha a chave da API do ExchangeRate Host:**
    - Acesse [ExchangeRate Host](https://exchangerate.host/)

    2. **Configure a chave no projeto:**
    - No arquivo `local.properties`, na raiz do projeto, adicione a chave:
    ```properties
    EXCHANGERATE_API_KEY=chave_da_api
    ```
4. **Sincronize as dependências:**
   - Faça a sincronização/build dos arquivos gradle

5. **Execute o projeto:**
   - Conecte um Android físico ou inicie um emulador
   - Clique no botão "Run"

## API utilizada

### ExchangeRate Host API
- **URL:** `https://api.exchangerate.host`
- **Endpoint:** `/convert`

## Decisões técnicas tomadas

- **MVVM (Model-View-ViewModel):** Arquitetura recomendada pelo Google para desenvolvimento Android Nativo, maior facilidade para manutenção, implementação de testes e features
- **Clean Architecture:** Dividido por camadas (presentation, domain, data) para melhor organização e separação de responsabilidades
- **Jetpack Compose:** Framework do Google para construção de interfaces declarativas
- **Material Design 3:** Diretrizes de design do Google
- **Dark Mode:** Opção de troca de modo escuro e claro do aplicativo, salvando localmente no dispositivo
- **StateFlow:** Para reatividade e observação de mudanças de estado
- **DataStore:** Para persistência de configurações do usuário
- **Retrofit:** Cliente HTTP type-safe para consumo da API
- **Moshi:** Serialização/deserialização de JSON's
- **OkHttp:** Cliente HTTP com interceptors customizados
- **Koin:** Service Locator, muito utilizado para DI em Kotlin e Android

### Estrutura do Projeto
```
├── data/
│   ├── datastore/    # Implementações do DataStore
│   ├── network/      # Configuração de rede e interceptors
│   └── repository/   # Implementações dos repositórios
├── di/               # Módulos de injeção de dependência
├── domain/
│   ├── datastore/    # Interfaces do DataStore
│   ├── model/        # Modelos de domínio
│   └── repository/   # Interfaces dos repositórios
└── presentation/
    ├── converter/    # Tela inicial, com conversão de moedas
    ├── history/      # Tela de histórico das conversões
    └── theme/        # Configurações de tema
```

## O que faria diferente com mais tempo

### Melhorias Técnicas
- **Testes:** Implementar testes unitários e instrumentados com JUnit, Espresso e MockK
- **Paginação:** Paginação na tela de histórico
- **Reorganizações:** Reorganizar arquivos de screen e componentes em seus devidos locais, além de refatorar para reutilizar código
- **Animações:** Adicionar animações para transição de tela
- **Ícone do APP:** Alterar ícone do app padrão
- **SplashScreen:** Adicionar SplashScreen
- **Crashlytics:** Integrar Firebase Crashlytics para monitoramento de crashes
- **Ofuscação/Proguard:** Implementar ofuscação de código para release
