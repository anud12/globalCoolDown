export const ActionsWsEndpoints = {
    teleport: (id) => `/ws/gameObject/${id}/action/teleport`,
    move: (id) => `/ws/gameObject/${id}/action/move`,
    create: (id) => `/ws/gameObject/${id}/action/create`,
};