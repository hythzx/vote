/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { VoteTestModule } from '../../../test.module';
import { VoteResultComponent } from '../../../../../../main/webapp/app/entities/vote-result/vote-result.component';
import { VoteResultService } from '../../../../../../main/webapp/app/entities/vote-result/vote-result.service';
import { VoteResult } from '../../../../../../main/webapp/app/entities/vote-result/vote-result.model';

describe('Component Tests', () => {

    describe('VoteResult Management Component', () => {
        let comp: VoteResultComponent;
        let fixture: ComponentFixture<VoteResultComponent>;
        let service: VoteResultService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoteTestModule],
                declarations: [VoteResultComponent],
                providers: [
                    VoteResultService
                ]
            })
            .overrideTemplate(VoteResultComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VoteResultComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoteResultService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new VoteResult(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.voteResults[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
