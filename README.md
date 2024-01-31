Microserviço que recebe uma requisicao via postman, salva em um banco mongo e um redis, envia via kafka para os topicos de pagamento e separacao, depois de processado nesses topicos
fica ouvindo via kafka os topicos de devolucao de pagamento e devolucao de separacao, atualiza os bancos mongo e redis. Por um tempo configuravel faz o sysnc entre os bancos mongo
e redis
