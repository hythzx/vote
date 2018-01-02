import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { VoteResult } from './vote-result.model';
import { VoteResultPopupService } from './vote-result-popup.service';
import { VoteResultService } from './vote-result.service';
import { VoteItem, VoteItemService } from '../vote-item';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-vote-result-dialog',
    templateUrl: './vote-result-dialog.component.html'
})
export class VoteResultDialogComponent implements OnInit {

    voteResult: VoteResult;
    isSaving: boolean;

    voteitems: VoteItem[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private voteResultService: VoteResultService,
        private voteItemService: VoteItemService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.voteItemService.query()
            .subscribe((res: ResponseWrapper) => { this.voteitems = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.voteResult.id !== undefined) {
            this.subscribeToSaveResponse(
                this.voteResultService.update(this.voteResult));
        } else {
            this.subscribeToSaveResponse(
                this.voteResultService.create(this.voteResult));
        }
    }

    private subscribeToSaveResponse(result: Observable<VoteResult>) {
        result.subscribe((res: VoteResult) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: VoteResult) {
        this.eventManager.broadcast({ name: 'voteResultListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackVoteItemById(index: number, item: VoteItem) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-vote-result-popup',
    template: ''
})
export class VoteResultPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private voteResultPopupService: VoteResultPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.voteResultPopupService
                    .open(VoteResultDialogComponent as Component, params['id']);
            } else {
                this.voteResultPopupService
                    .open(VoteResultDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
