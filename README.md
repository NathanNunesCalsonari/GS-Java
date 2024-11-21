# Projeto Nexus - Sistema Inteligente de Redução do Consumo Energético

## Integrantes
 - Ana Beatriz Bento da Silva - rm552536 (2TDSR)
 - Geovana Ribeiro Domingos Silva - rm99646 (2TDSR)
 - Leonardo Camargo Lucena - rm552537 (2TDSR)
 - Nathan Nunes Calsonari - rm552539 (2TDSR)
 - Ruan Guedes de Campo - rm551096 (2TDSR)

## Vídeo Pitch do projeto

## Vídeo do funcionamento do projeto

[https://www.youtube.com/watch?v=--p1kaKXmak](https://www.youtube.com/watch?v=sr7Z_mlDJ3I)

## Link do site na nuvem

https://app-nexus-552539.azurewebsites.net/home

## Arquitetura de solução

![image](https://github.com/user-attachments/assets/c5681d2a-934d-4a88-b717-f67e855e8803)

## Descrição do Projeto

O **Projeto Nexus** é uma plataforma inteligente criada com o objetivo de monitorar e reduzir o consumo de energia em tempo real. Com o uso de tecnologias emergentes, como **Internet das Coisas (IoT)**, **Inteligência Artificial (IA)** e **Automação**, o projeto visa otimizar o uso de energia em diferentes contextos, como indústrias, residências e cidades inteligentes. A ideia é criar um ambiente em que a gestão de energia seja inteligente e responsiva, identificando desperdícios e automatizando ajustes para reduzir o consumo de energia sem comprometer a qualidade de vida ou a produtividade.

## Objetivo

O principal objetivo do **Projeto Nexus** é desenvolver uma plataforma inteligente que monitore e reduza o consumo de energia em tempo real. A plataforma utilizará recursos avançados de **IoT** para coletar dados dos dispositivos conectados, **IA** para analisar e prever o consumo de energia, e **automação** para ajustar dinamicamente os dispositivos e sistemas de forma eficiente. O foco do projeto é promover a **sustentabilidade** e a **eficiência energética**, permitindo que ambientes domésticos, industriais e urbanos se beneficiem de uma gestão energética mais eficaz.

## Arquitetura do Sistema

A arquitetura do sistema Nexus foi projetada para ser escalável e modular, permitindo fácil integração com diferentes tipos de dispositivos IoT. O sistema será baseado em nuvem, garantindo acessibilidade, segurança e a possibilidade de expansão para novos ambientes, sejam eles residenciais, industriais ou urbanos. O sistema contará com três camadas principais:

1. **Sensores e Dispositivos**: Dispositivos conectados que monitoram o consumo de energia e fatores ambientais, como temperatura, umidade e presença de pessoas. Esses dados serão registrados através de um serviço web.
2. **Plataforma de Análise e Automação**: Backend que processa os dados em tempo real, executa algoritmos de otimização e controla os dispositivos conectados.
3. **Interface de Usuário**: Aplicativo ou interface para visualização de dados e controle dos dispositivos, além de gerar relatórios detalhados sobre o consumo de energia.

## Tecnologias Utilizadas

- **Java**: A plataforma será desenvolvida utilizando **Java**, um dos principais frameworks para sistemas de grande escala. A escolha do Java visa garantir robustez, escalabilidade e facilidade de manutenção. O framework **Spring Boot** será utilizado para criar a API do backend, oferecendo uma estrutura flexível e segura para o processamento em tempo real dos dados.

- **Internet das Coisas (IoT)**: Sensores conectados a dispositivos inteligentes irão coletar dados sobre o consumo de energia e fatores ambientais. Esses dados serão registrados no banco de dados por meio de um serviço web.

- **Inteligência Artificial (IA)**: Algoritmos de IA serão aplicados para analisar os dados de consumo e prever o comportamento dos usuários, ajustando automaticamente os sistemas para otimizar o consumo de energia.

- **Automação**: A plataforma automatiza a gestão do consumo energético, ajustando os dispositivos em tempo real com base nas análises realizadas pela IA.

- **Plataforma em Nuvem**: A infraestrutura será baseada na nuvem, permitindo que o sistema seja acessado de qualquer lugar, além de possibilitar a expansão contínua.

## Implementação em Java

O backend do **Projeto Nexus** é desenvolvido em **Java** utilizando o framework **Spring Boot**, com o objetivo de processar dados em tempo real sobre o consumo de energia. A plataforma coleta informações de sensores e dispositivos conectados para otimizar o consumo energético, com foco em eficiência e sustentabilidade. A arquitetura do sistema inclui os seguintes elementos principais:

### Sensores e Dispositivos

- O sistema coleta dados de **sensores IoT** conectados a dispositivos. Cada dispositivo é responsável por monitorar o consumo de energia em tempo real e registrar variáveis ambientais, como temperatura e umidade.
- Esses dados são registrados e enviados para o banco de dados por meio de um **serviço web**.

### Armazenamento e Processamento de Dados

- O sistema armazena os dados coletados dos dispositivos e sensores em um **banco de dados**. A partir dessas informações, o sistema pode calcular o consumo energético total e gerar relatórios históricos.
- Os dados dos sensores são processados para gerar um histórico do consumo de energia, ajudando a identificar padrões e otimizar o uso de energia.

### Plataforma de Backend

- O backend, desenvolvido em **Java com Spring Boot**, processa os dados em tempo real, gerencia o registro e controle dos sensores e dispositivos, e fornece uma **API** para a interface do usuário.
- A plataforma é responsável por fornecer uma base de dados acessível para análise do consumo energético, permitindo que usuários consultem informações e visualizem relatórios detalhados sobre o consumo.

### Banco de Dados

- A plataforma utiliza um **banco de dados** para armazenar informações sobre sensores, dispositivos e consumo de energia. Isso permite consultas e a geração de relatórios de consumo energético ao longo do tempo, oferecendo dados valiosos para a otimização de energia.

## Benefícios Alcançados para o Negócio

O **Projeto Nexus** proporciona uma série de benefícios para os negócios e usuários ao promover a **eficiência energética** e a **sustentabilidade**. A seguir, estão os principais benefícios alcançados:

1. **Redução de Custos com Energia**: A plataforma permite uma gestão inteligente do consumo energético, o que resulta em uma significativa redução nos custos operacionais, tanto para empresas quanto para residências.
2. **Eficiência Operacional**: Ao automatizar o ajuste dos dispositivos de consumo, o sistema melhora a eficiência operacional, reduzindo desperdícios e otimizando o uso de energia.
3. **Sustentabilidade**: Com a redução do consumo de energia e a otimização dos recursos, o projeto contribui para um ambiente mais sustentável, alinhando-se com as metas globais de redução de emissões de carbono e uso responsável dos recursos naturais.
4. **Facilidade de Implementação**: O sistema modular e baseado em nuvem facilita a implementação e escalabilidade para diferentes tipos de negócios e ambientes, desde indústrias até áreas residenciais e urbanas.
5. **Relatórios Detalhados e Análises**: A geração de relatórios detalhados sobre o consumo de energia oferece insights valiosos para a tomada de decisões informadas e melhorias contínuas no consumo energético.

