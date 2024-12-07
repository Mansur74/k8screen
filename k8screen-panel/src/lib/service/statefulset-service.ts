import type { StatefulSet } from "$lib/model/StatefulSet";
import { SPRING_BASE_URL } from "$lib/utils/utils";
import { applyGetRequest, applyPutRequest } from "./http-request";

export const statefulSetAPI = {
  getAllStatefulSets: async (namespace: string): Promise<StatefulSet[]> => {
    const url = `${SPRING_BASE_URL}/api/kubernetes/namespaces/${namespace}/stateful-sets`;
    return await (await applyGetRequest(url)).json();
  },

  getStatefulSetDetails: async (namespace: string, name: string): Promise<string> => {
    const url = `${SPRING_BASE_URL}/api/kubernetes/namespaces/${namespace}/stateful-sets/${name}/details`;
    return await (await applyGetRequest(url)).text();
  },

  updateStatefulSet: async (namespace: string, name: string, body: any): Promise<any> => {
    const url = `${SPRING_BASE_URL}/api/kubernetes/namespaces/${namespace}/stateful-sets/${name}`;
    return await (await applyPutRequest(url, JSON.stringify(body))).json();
  }
}
