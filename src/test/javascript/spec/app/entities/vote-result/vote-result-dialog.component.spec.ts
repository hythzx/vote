/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VoteTestModule } from '../../../test.module';
import { VoteResultDialogComponent } from '../../../../../../main/webapp/app/entities/vote-result/vote-result-dialog.component';
import { VoteResultService } from '../../../../../../main/webapp/app/entities/vote-result/vote-result.service';
import { VoteResult } from '../../../../../../main/webapp/app/entities/vote-result/vote-result.model';
import { VoteItemService } from '../../../../../../main/webapp/app/entities/vote-item';

describe('Component Tests', () => {

    describe('VoteResult Management Dialog Component', () => {
        let comp: VoteResultDialogComponent;
        let fixture: ComponentFixture<VoteResultDialogComponent>;
        let service: VoteResultService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoteTestModule],
                declarations: [VoteResultDialogComponent],
                providers: [
                    VoteItemService,
                    VoteResultService
                ]
            })
            .overrideTemplate(VoteResultDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VoteResultDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoteResultService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new VoteResult(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.voteResult = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'voteResultListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new VoteResult();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.voteResult = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'voteResultListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
