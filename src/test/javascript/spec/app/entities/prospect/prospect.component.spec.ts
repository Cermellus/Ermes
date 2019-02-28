/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ErmesTestModule } from '../../../test.module';
import { ProspectComponent } from 'app/entities/prospect/prospect.component';
import { ProspectService } from 'app/entities/prospect/prospect.service';
import { Prospect } from 'app/shared/model/prospect.model';

describe('Component Tests', () => {
    describe('Prospect Management Component', () => {
        let comp: ProspectComponent;
        let fixture: ComponentFixture<ProspectComponent>;
        let service: ProspectService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [ProspectComponent],
                providers: []
            })
                .overrideTemplate(ProspectComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProspectComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProspectService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Prospect(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.prospects[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
