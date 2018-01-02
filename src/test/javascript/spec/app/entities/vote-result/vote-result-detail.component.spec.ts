/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { VoteTestModule } from '../../../test.module';
import { VoteResultDetailComponent } from '../../../../../../main/webapp/app/entities/vote-result/vote-result-detail.component';
import { VoteResultService } from '../../../../../../main/webapp/app/entities/vote-result/vote-result.service';
import { VoteResult } from '../../../../../../main/webapp/app/entities/vote-result/vote-result.model';

describe('Component Tests', () => {

    describe('VoteResult Management Detail Component', () => {
        let comp: VoteResultDetailComponent;
        let fixture: ComponentFixture<VoteResultDetailComponent>;
        let service: VoteResultService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoteTestModule],
                declarations: [VoteResultDetailComponent],
                providers: [
                    VoteResultService
                ]
            })
            .overrideTemplate(VoteResultDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VoteResultDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoteResultService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new VoteResult(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.voteResult).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
