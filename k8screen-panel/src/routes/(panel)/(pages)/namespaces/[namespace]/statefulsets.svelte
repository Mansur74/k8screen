<script lang="ts">
  import Bar from '$lib/components/bar.svelte';
  import IconKebabMenu from '$lib/components/icons/IconKebabMenu.svelte';
  import Pagination from '$lib/components/pagination.svelte';
  import Terminal from '$lib/components/terminal.svelte';
  import Button from '$lib/components/ui/button/button.svelte';
  import * as DropdownMenu from '$lib/components/ui/dropdown-menu';
  import * as Table from '$lib/components/ui/table';
  import { OptionTerminal } from '$lib/model/enum';
  import type { StatefulSet } from '$lib/model/StatefulSet';
  import { statefulSetAPI } from '$lib/service/statefulset-service';
  import * as yaml from 'yaml';

  export let namespace;

  let size: number = 5;

  let loading = true;
  let loadingTable = false;
  let option: OptionTerminal;
  let details: string;

  let statefulSets: StatefulSet[] = [];
  let paginated: StatefulSet[] = [];
  let k8sItem: string;
  let open: boolean;

  $: if (namespace) {
    getAllStatefulSets();
  }

  const load = (service: string) => {
    k8sItem = service;
    open = true;
    option = OptionTerminal.DETAIL;
    getDetails();
  };

  const getAllStatefulSets = async () => {
    try {
      loadingTable = true;
      statefulSets = await statefulSetAPI.getAllStatefulSets(namespace);
    } finally {
      loadingTable = false;
    }
  };

  const getDetails = async (): Promise<string> => {
    loading = true;
    details = await statefulSetAPI.getStatefulSetDetails(namespace, k8sItem);
    loading = false;
    return details;
  };

  const updateItem = async (editedStatefulSet: string) => {
    try {
      loading = true;
      return statefulSetAPI.updateStatefulSet(namespace, k8sItem, yaml.parse(editedStatefulSet));
    } finally {
      loading = false;
    }
  };
</script>

<div class="flex flex-col" style="height: calc(100vh - 150px);">
  <div class="flex-grow flex flex-col gap-8 justify-between overflow-auto">
    <Table.Root>
      <Table.Header>
        <Table.Row>
          <Table.Head>NAME</Table.Head>
          <Table.Head>READY</Table.Head>
          <Table.Head>AGE</Table.Head>
          <Table.Head></Table.Head>
        </Table.Row>
      </Table.Header>
      <Table.Body>
        {#if loadingTable}
          <Table.Row>
            <Table.Cell><Bar /></Table.Cell>
            <Table.Cell><Bar /></Table.Cell>
            <Table.Cell><Bar /></Table.Cell>
            <Table.Cell><Bar /></Table.Cell>
          </Table.Row>
        {:else}
          {#each paginated as statefulSet}
            <Table.Row
              on:click={() => {
                load(statefulSet.name);
              }}
              class="cursor-pointer"
            >
              <Table.Cell>{statefulSet.name}</Table.Cell>
              <Table.Cell>{statefulSet.ready_replicas}/{statefulSet.total_replicas}</Table.Cell>
              <Table.Cell>{statefulSet.age}</Table.Cell>
              <Table.Cell>
                <DropdownMenu.Root>
                  <DropdownMenu.Trigger>
                    <Button class="p-2 h-auto" variant="ghost">
                      <IconKebabMenu />
                    </Button>
                  </DropdownMenu.Trigger>
                  <DropdownMenu.Content align="end">
                    <DropdownMenu.Group>
                      <DropdownMenu.Item onclick={() => (option = OptionTerminal.DETAIL)}>View</DropdownMenu.Item>
                      <DropdownMenu.Item onclick={() => (option = OptionTerminal.EDIT)}>Edit</DropdownMenu.Item>
                      <DropdownMenu.Item>Delete</DropdownMenu.Item>
                    </DropdownMenu.Group>
                  </DropdownMenu.Content>
                </DropdownMenu.Root>
              </Table.Cell>
            </Table.Row>
          {/each}
        {/if}
      </Table.Body>
    </Table.Root>
    <div class="mb-5">
      <Pagination bind:pageSize={size} data={statefulSets} bind:paginated={paginated} />
    </div>
  </div>

  <Terminal
    type="stateful-set"
    getDetails={getDetails}
    updateItem={updateItem}
    k8sItem={k8sItem}
    option={option}
    details={details}
    loading={loading}
    open={open}
  />
</div>
