/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { VoteTestModule } from '../../../test.module';
import { VoteDetailComponent } from '../../../../../../main/webapp/app/entities/vote/vote-detail.component';
import { VoteService } from '../../../../../../main/webapp/app/entities/vote/vote.service';
import { Vote } from '../../../../../../main/webapp/app/entities/vote/vote.model';

describe('Component Tests', () => {

    describe('Vote Management Detail Component', () => {
        let comp: VoteDetailComponent;
        let fixture: ComponentFixture<VoteDetailComponent>;
        let service: VoteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoteTestModule],
                declarations: [VoteDetailComponent],
                providers: [
                    VoteService
                ]
            })
            .overrideTemplate(VoteDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VoteDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Vote(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.vote).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
