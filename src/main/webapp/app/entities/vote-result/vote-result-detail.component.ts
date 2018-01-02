import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { VoteResult } from './vote-result.model';
import { VoteResultService } from './vote-result.service';

@Component({
    selector: 'jhi-vote-result-detail',
    templateUrl: './vote-result-detail.component.html'
})
export class VoteResultDetailComponent implements OnInit, OnDestroy {

    voteResult: VoteResult;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private voteResultService: VoteResultService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVoteResults();
    }

    load(id) {
        this.voteResultService.find(id).subscribe((voteResult) => {
            this.voteResult = voteResult;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVoteResults() {
        this.eventSubscriber = this.eventManager.subscribe(
            'voteResultListModification',
            (response) => this.load(this.voteResult.id)
        );
    }
}
