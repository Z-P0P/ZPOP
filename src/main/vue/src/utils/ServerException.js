export class ServerException extends Error {
  /**
   * fetch api를 이용한 요청중 예외가 발생했을때 사용하는 예외.
   *
   * @param {Response} res fetch api response
   */
  constructor(res) {
    super();
    this.res = res;
  }
}
