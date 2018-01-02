import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { VoteResult } from './vote-result.model';
import { VoteResultPopupService } from './vote-result-popup.service';
import { VoteResultService } from './vote-result.service';

@Component({
    selector: 'jhi-vote-result-delete-dialog',
    templateUrl: './vote-result-delete-dialog.component.html'
})
export class VoteResultDeleteDialogComponent {

    voteResult: VoteResult;

    constructor(
        private voteResultService: VoteResultService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.voteResultService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'voteResultListModification',
                content: 'Deleted an voteResult'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-vote-result-delete-popup',
    template: ''
})
export class VoteResultDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private voteResultPopupService: VoteResultPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.voteResultPopupService
                .open(VoteResultDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
