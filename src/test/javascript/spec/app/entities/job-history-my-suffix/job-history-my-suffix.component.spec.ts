/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { VoteTestModule } from '../../../test.module';
import { JobHistoryMySuffixComponent } from '../../../../../../main/webapp/app/entities/job-history-my-suffix/job-history-my-suffix.component';
import { JobHistoryMySuffixService } from '../../../../../../main/webapp/app/entities/job-history-my-suffix/job-history-my-suffix.service';
import { JobHistoryMySuffix } from '../../../../../../main/webapp/app/entities/job-history-my-suffix/job-history-my-suffix.model';

describe('Component Tests', () => {

    describe('JobHistoryMySuffix Management Component', () => {
        let comp: JobHistoryMySuffixComponent;
        let fixture: ComponentFixture<JobHistoryMySuffixComponent>;
        let service: JobHistoryMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoteTestModule],
                declarations: [JobHistoryMySuffixComponent],
                providers: [
                    JobHistoryMySuffixService
                ]
            })
            .overrideTemplate(JobHistoryMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JobHistoryMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobHistoryMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new JobHistoryMySuffix(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.jobHistories[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
