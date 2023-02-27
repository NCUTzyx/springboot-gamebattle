const ModuleReview = {

    state: {
        is_review: false,
        a_steps: "",
        b_steps: "",
        review_loser: "",
    },
    getters: {
    },
    mutations: {
        updateIsReview(state,is_review) {
            state.is_review = is_review;
        },
        updateSteps(state,data) {
            state.a_steps = data.a_steps;
            state.b_steps = data.b_steps;
        },
        updateReviewLoser(state,loser){
            state.review_loser = loser;
        },

    },
    actions: {
    },
    modules: {
    }
};

export default ModuleReview;