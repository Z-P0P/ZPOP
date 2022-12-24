/**
 * parameterObj를 기반으로 페이징 요청 url을 리턴한다
 */
export function generateUrl(parameterObj) {
  const searchParams = new URLSearchParams(parameterObj).toString();
  return `/meeting/api/list?${searchParams}`;
}